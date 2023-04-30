<?php

namespace App\Http\Controllers\API\Game;

use App\Helpers\ImageHelper;
use App\Http\Controllers\API\APIController;
use App\Http\Controllers\Controller;
use App\Models\Game;
use Illuminate\Http\Request;

class GameController extends APIController
{
    public function __construct(){

        $this->middleware('auth:api');

    }

    public function list(Request $request){

        $games = Game::where('status', 'Active')->where('is_deleted', 0)->latest('id')->get();

        if($games->isEmpty()){
            return parent::apiResponse(false, 'No games found', null);
        }

        foreach($games as $game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');
        }

        parent::log($request , 'View Game List');

        return parent::apiResponse(true, null, $games);
    }

    public function details(Request $request, $id){

        $currentUser = auth('api')->user();

        $game = Game::where('id', $id)->where('status', 'Active')->where('is_deleted', 0)->first();

        if($game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');

            // get all review of if the status is active and is_deleted is 0 but if review is from the current user then get all review
            $allReviews = $game->reviews()
                    ->where(function($query) use ($currentUser){
                        $query->where('user_id', $currentUser->id)->orWhere('status', 'Active');
                    })
                    ->where('is_deleted', 0)
                    ->get()->sortByDesc('id');

            $reviews = array();

            foreach($allReviews as $review){
                if($review->user_id == $currentUser->id){
                    $review->is_mine = true;
                }else{
                    $review->is_mine = false;
                }
                $review->user = $review->user()->first()->profile()->first();

                $reviews[] = $review;
            }

            // sort $reviews by the currentuser id
            usort($reviews, function($a, $b) use ($currentUser) {
                return $a->user_id == $currentUser->id ? -1 : 1;
            });

            in_array($currentUser->id, $allReviews->pluck('user_id')->toArray()) ? $game->is_mine_review = true : $game->is_mine_review = false;


            $game->reviews = $reviews;

            parent::log($request , 'View Game Details' . ' - ' . $game->slug);

            return parent::apiResponse(true, null, $game);
        }else{
            return parent::apiResponse(false, 'Game not found', null);
        }

    }
}
