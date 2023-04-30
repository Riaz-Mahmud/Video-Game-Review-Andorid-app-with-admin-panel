<?php

namespace App\Http\Controllers\Backend;

use App\Models\Game;

use App\Models\User;
use App\Models\Review;
use App\Models\Session;
use Illuminate\Http\Request;

class HomeController extends BackendController
{

    function index(Request $request){

        $data = [];

        parent::log($request , 'View Dashboard');

        $data['total_users'] = User::count();
        $data['total_reviews'] = Review::where('status', 'Active')->where('is_deleted', 0)->count();
        $data['total_games'] = Game::where('status', 'Active')->where('is_deleted', 0)->count();
        $data['total_live_users'] = Session::activity(10)->count();

        return view('backend.pages.home.index')->with('data', $data);
    }
}
