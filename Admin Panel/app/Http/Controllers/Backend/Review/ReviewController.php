<?php

namespace App\Http\Controllers\Backend\Review;

use App\Models\Game;
use App\Models\Review;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Session;
use App\Http\Controllers\Backend\BackendController;
use App\Services\GameService;

class ReviewController extends BackendController
{
    public function index(Request $request){

        $data = array();

        $data['rows'] = Review::where('is_deleted', 0)->where('status','Pending')->latest('id')->paginate(20);

        foreach($data['rows'] as $row){
            $row->hashId = Crypt::encrypt($row->id);
            $row->user =  $row->user;
            $row->game =  $row->game;
        }

        parent::log($request , 'View Review List');

        return view('backend.pages.review.index')->with('data', $data);
    }

    public function updateStatus(Request $request){

        try{

            $review = Review::find(Crypt::decrypt($request->id));
            if(!$review){
                Session::flash('error', 'Review not found');
                return redirect()->back();
            }

            $update = Review::where('id', $review->id)->update([
                'status' => $request->status,
            ]);

            if(!$update){
                Session::flash('error', 'Review status not updated');
                return redirect()->back();
            }

            \Facades\App\Services\GameService::updateGameReview($review->game_id);

            parent::log($request , 'Update Review Status' . ' - ' . $review->id);

            Session::flash('success', 'Review status updated successfully');
            return redirect()->back();

        }catch(\Exception $e){
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }

    }

    public function destroy(Request $request, $id){

        try{

            $review = Review::find(Crypt::decrypt($id));
            if(!$review){
                Session::flash('error', 'Review not found');
                return redirect()->back();
            }

            $delete = Review::where('id', $review->id)->update([
                'is_deleted' => 1,
            ]);

            if(!$delete){

                Session::flash('error', 'Review not deleted');
                return redirect()->back();
            }

            \Facades\App\Services\GameService::updateGameReview($review->game_id);

            parent::log($request , 'Delete Review' . ' - ' . $review->id);

            Session::flash('success', 'Review deleted successfully');
            return redirect()->back();

        }catch(\Exception $e){
            dd($e->getMessage() , $e->getLine());
            Session::flash('error', $e->getMessage());
            return redirect()->back();
        }

    }
}
