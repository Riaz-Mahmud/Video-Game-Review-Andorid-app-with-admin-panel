<?php

namespace App\Http\Controllers\Backend\Game;

use App\Helpers\StringHelper;
use App\Models\User;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Crypt;
use Intervention\Image\Facades\Image;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Storage;
use App\Http\Controllers\Backend\BackendController;
use App\Http\Requests\Backend\Game\GameRequest;
use App\Models\Game;
use App\Models\Profile;

class GameController extends BackendController
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request){

        $data=[];

        $data['rows'] = Game::where('is_deleted', 0)->latest('id')->paginate(20);

        foreach($data['rows'] as $row){
            $row->hashId = Crypt::encrypt($row->id);
            $row->sale_expiry_date = $row->sale_expiry_date ? date('d-m-Y', strtotime($row->sale_expiry_date)) : 'N/A' ;
            $row->description = StringHelper::desc(strip_tags($row->description), 300);
        }

        parent::log($request , 'View Game List');

        return view('backend.pages.game.index')->with('data', $data);

    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request){
        $data = [];

        parent::log($request , 'View Game Create');

        return view('backend.pages.game.create')->with('data', $data);

    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(GameRequest $request)
    {
        try{
            $slug = 'game/'.Str::slug($request->name, '-');
            if(count(Game::where('slug', $slug)->where('is_deleted', 0)->get()) > 0){
                $slug = $slug.'-'.date('YmdHis');
            }

            DB::beginTransaction();

            $game = Game::create([
                'name' => $request->name,
                'slug' => $slug,
                'description' => $request->description ?? null,
                'status' => $request->status,
            ]);

            if(!$game){
                Session::flash('error', 'Game not created');
                return redirect()->back();
            }

            if($request->hasFile('image')){
                $this->uploadImage($request->file('image'), $game);
            }

            parent::log($request , 'Create Game');

            DB::commit();
            Session::flash('success', 'Game created successfully');
            return redirect()->route('admin.games.index');

        }catch(\Exception $e){
            DB::rollBack();
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }
    }

    protected function uploadImage($file, $game){

        if(!is_dir(storage_path("app/public/assets/games/".$game->id))){
            mkdir(storage_path("app/public/assets/games/".$game->id) , 0777, true);
        }

        $file = $file;
        $file_ext = $file->getClientOriginalExtension();
        $file_name = date('YmdHis').rand(1000, 9999);
        $file_full_name = $file_name.'.'.$file_ext;
        $upload_path = 'assets/games/'.$game->id.'/'. $file_full_name;
        Storage::disk('public')->put($upload_path, file_get_contents($file));

        $game->banner = $upload_path;
        $game->save();

        foreach(config('backend.games.image_sizes') as $key => $size){
            try{
                $fileName = $file_name.'_'.$key. '.' .$file_ext;

                $thumbnail = Image::make($file);
                $thumbnail->resize($size['width'], $size['height'],function ($constraint) {
                    $constraint->aspectRatio();
                })->save(storage_path('app/public/assets/games/'.$game->id.'/'.$fileName));

            }catch(\Exception $e){
                dd($e->getMessage(), $e->getLine());
                continue;
            }
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show(Request $request, $slug){
        $slug = 'game/'.$slug;

        $data = array();

        $data['row'] = Game::where('slug', $slug)->first();

        if(!$data['row']){
            Session::flash('error', 'Game not found');
            return redirect()->back();
        }

        $data['row']->hashId = Crypt::encrypt($data['row']->id);

        $data['row']->reviews = $data['row']->reviews()->where('is_deleted', 0)->latest('id')->paginate(10);

        foreach($data['row']->reviews as $review){
            $review->hashId = Crypt::encrypt($review->id);
            $review->user = Profile::find($review->user_id);
            $review->game = Game::find($review->game_id);
        }

        parent::log($request , 'View Game Details' . ' - ' . $slug);

        return view('backend.pages.game.show')->with('data', $data);
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit(Request $request ,$id){

        try{
            $data =[];

            $data['row'] = Game::find(Crypt::decrypt($id));

            if(!$data['row']){
                Session::flash('error', 'Game not found');
                return redirect()->back();
            }

            $data['row']['hashId'] = $id;

            parent::log($request , 'View Game Edit');

            return view('backend.pages.game.edit')->with('data', $data);

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }

    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(GameRequest $request, $id){
        try{

            $id = Crypt::decrypt($id);

            $game = Game::find($id);
            if(!$game){
                Session::flash('error', 'Games not found');
                return redirect()->back();
            }

            $slug = 'game/'.Str::slug($request->name, '-');
            if(count(Game::where('slug', $slug)->where('is_deleted', 0)->where('id', '!=', $game->id)->get()) > 0){
                $slug = $slug.'-'.date('YmdHis');
            }

            DB::beginTransaction();

            $update = $game->update([
                'name' => trim($request->name),
                'slug' => $slug,
                'description' => $request->description ?? null,
                'status' => $request->status,
            ]);

            if(!$update){
                Session::flash('error', 'Games not updated');
                return redirect()->back();
            }

            if($request->hasFile('image')){
                $this->uploadImage($request->file('image'), $game);
            }

            parent::log($request , 'Update Game' . ' - ' . $game->name . ' - ' . $game->id);

            DB::commit();
            Session::flash('success', 'Game updated successfully');
            return redirect()->route('admin.games.index');

        }catch(\Exception $e){
            DB::rollBack();
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }

    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request ,$id){

        try{

            $game = Game::find(Crypt::decrypt($id));
            if(!$game){
                Session::flash('error', 'Game not found');
                return redirect()->back();
            }

            $delete = Game::where('id', $game->id)->update([
                'is_deleted' => 1,
            ]);

            if(!$delete){
                Session::flash('error', 'Game not deleted');
                return redirect()->back();
            }

            parent::log($request , 'Delete Game ' . ' - ' . $game->name . ' - ' . $game->id);

            Session::flash('success', 'Game deleted successfully');
            return redirect()->route('admin.games.index');

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }

    }
}
