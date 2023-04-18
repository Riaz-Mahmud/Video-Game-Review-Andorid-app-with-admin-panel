<?php

namespace App\Http\Controllers\Backend;

use App\Models\News;

use App\Models\User;
use App\Models\Profile;
use App\Models\Session;
use App\Helpers\Helpers;
use Jenssegers\Agent\Agent;
use App\Jobs\ActivityLogJob;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class HomeController extends BackendController
{

    function index(Request $request){

        $data = [];

        parent::log($request , 'View Dashboard');

        return view('backend.pages.home.index')->with('data', $data);
    }
}
