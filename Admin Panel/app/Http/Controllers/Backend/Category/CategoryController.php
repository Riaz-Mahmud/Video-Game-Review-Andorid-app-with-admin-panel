<?php

namespace App\Http\Controllers\Backend\Category;

use App\Models\Category;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Session;
use App\Http\Requests\Backend\Category\CategoryRequest;

class CategoryController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index(Request $request){

        $data=[];

        $data['rows'] = Category::where('is_deleted', 0)->latest('id')->paginate(20);

        foreach($data['rows'] as $row){
            $row->hashId = Crypt::encrypt($row->id);
        }

        return view('backend.pages.category.index')->with('data', $data);

    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create(Request $request){

        return view('backend.pages.category.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(CategoryRequest $request){
        try{

            $slug = 'category/' . str_replace(' ', '-', strtolower($request->title));
            if(Category::where('slug', $slug)->exists()){
                Session::flash('error', 'Category already exists');
                return back();
            }

            $create = Category::create([
                'title' => $request->title,
                'slug' => $slug,
                'description' => $request->description ?? null,
                'status' => $request->status,
            ]);

            if(!$create){
                Session::flash('error', 'Something went wrong');
                return back();
            }

            Session::flash('success', 'Category created successfully');
            return redirect()->route('admin.category.index');

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return back();
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function edit(Request $request, $id){
        try{

            $id = Crypt::decrypt($id);

            $data = [];

            $data['row'] = Category::find($id);
            if(!$data['row']){
                Session::flash('error', 'Category not found');
                return back();
            }

            $data['row']->hashId = Crypt::encrypt($data['row']->id);

            return view('backend.pages.category.edit')->with('data', $data);

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return back();
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(CategoryRequest $request, $id){
        try{

            $id = Crypt::decrypt($id);

            $category = Category::find($id);
            if(!$category){
                Session::flash('error', 'Category not found');
                return back();
            }

            if($category->title == $request->title){
                $slug = $category->slug;
            }else{
                $slug = 'category/' . str_replace(' ', '-', strtolower($request->title));

                if(Category::where('slug', $slug)->exists()){
                    Session::flash('error', 'Category already exists');
                    return back();
                }
            }

            $update = $category->update([
                'title' => $request->title,
                'slug' => $slug,
                'description' => $request->description ?? null,
                'status' => $request->status,
            ]);

            if(!$update){
                Session::flash('error', 'Something went wrong');
                return back();
            }

            Session::flash('success', 'Category updated successfully');
            return redirect()->route('admin.category.index');

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return back();
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy(Request $request, $id){
        try{

            $id = Crypt::decrypt($id);

            $category = Category::find($id);
            if(!$category){
                Session::flash('error', 'Category not found');
                return back();
            }

            $delete = $category->update([
                'is_deleted' => 1,
            ]);

            if(!$delete){
                Session::flash('error', 'Something went wrong');
                return back();
            }

            Session::flash('success', 'Category deleted successfully');
            return redirect()->route('admin.category.index');

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return back();
        }
    }
}
