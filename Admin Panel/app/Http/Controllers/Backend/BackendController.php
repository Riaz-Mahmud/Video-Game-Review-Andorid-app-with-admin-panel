<?php

namespace App\Http\Controllers\Backend;

use App\Http\Controllers\Controller;
use App\Jobs\ActivityLogJob;
use Illuminate\Support\Facades\Auth;
use Illuminate\Http\Request;
use Jenssegers\Agent\Agent;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Route;

class BackendController extends Controller{

    protected function log(Request $request, $msg = null){

        // try{
        //     $log = [
        //         'user_id' => Auth::check() ? Auth::user()->id : null,
        //         'description' => $msg ?? null,
        //         'url' => $request->getRequestUri() ?? null,
        //         'method' => $request->method() ?? null,
        //         'route' => Route::currentRouteName() ?? null,
        //         'ip' => $request->ip() ?? null,
        //         'agent' => $request->userAgent() ?? null,
        //         'device_data' => new Agent(),
        //     ];

        //     ActivityLogJob::dispatch($log);

        // }catch(\Exception $e){
        //     Log::info('Activity Log Error: ' . $e->getMessage());
        // }
    }
}
