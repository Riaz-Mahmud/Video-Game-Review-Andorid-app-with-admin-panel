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

        $games = Game::where('status', 'Active')->where('is_deleted', 0)->get();

        foreach($games as $game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');
        }

        return parent::apiResponse(true, null, $games);
    }

    public function details(Request $request, $id){

        $game = Game::where('id', $id)->where('status', 'Active')->where('is_deleted', 0)->first();

        if($game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');

            $game->reviews = $game->reviews()->where('status', 'Active')->where('is_deleted', 0)->get();

            return parent::apiResponse(true, null, $game);
        }else{
            return parent::apiResponse(false, 'Game not found', null);
        }

    }
}
