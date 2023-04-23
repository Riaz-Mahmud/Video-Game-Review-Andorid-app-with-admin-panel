<?php

namespace App\Http\Controllers\API\Review;

use App\Models\Game;
use App\Models\Review;
use Illuminate\Http\Request;
use App\Services\GeocodeLocation;
use Illuminate\Support\Facades\Validator;
use App\Http\Controllers\API\APIController;

class ReviewController extends APIController
{
    public function __construct(){

        $this->middleware('auth:api');

    }

    public function store(Request $request, $gameId = null){

        if($gameId == null){
            return parent::apiResponse(false, 'Game id is required', null);
        }


        $validator = Validator::make($request->all(),
            [
                'rating' => 'required',
                'message' => 'required',
                'latitude' => 'required',
                'longitude' => 'required',
            ]);
        if ($validator->fails()) {
            return parent::apiResponse(false, $validator->errors()->first(), null);
        }

        $currentUser = auth('api')->user();

        $game = Game::where('id', $gameId)->where('status', 'Active')->where('is_deleted', 0)->first();

        if($game){

            try{
                $review = new Review();
                $review->user_id = $currentUser->id;
                $review->game_id = $game->id;
                $review->rating = $request->rating;
                $review->comments = $request->message;
                $review->latitude = $request->latitude;
                $review->longitude = $request->longitude;
                $review->ip_address = $request->ip();
                $review->status = 'Pending';
                $review->is_deleted = 0;

                $data = (new GeocodeLocation)->coordinatesToAddress($request->latitude, $request->longitude);
                if($data['status'] == 'success'){
                    $review->address = $data['address'];
                }else{
                    $review->address = null;
                }

                $save = $review->save();

                if($save){
                    return parent::apiResponse(true, 'Review added successfully, waiting for admin approval', null);
                }else{
                    return parent::apiResponse(false, 'Something went wrong', null);
                }
            }catch(\Exception $e){
                return parent::apiResponse(false, $e->getMessage(), null);
            }

        }else{
            return parent::apiResponse(false, 'Game not found', null);
        }

    }
}
