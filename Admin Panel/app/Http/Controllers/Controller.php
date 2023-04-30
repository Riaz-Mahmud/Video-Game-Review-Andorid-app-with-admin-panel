<?php

namespace App\Http\Controllers;

use Jenssegers\Agent\Agent;
use App\Jobs\ActivityLogJob;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;
use Illuminate\Foundation\Bus\DispatchesJobs;
use Illuminate\Routing\Controller as BaseController;
use Illuminate\Foundation\Validation\ValidatesRequests;
use Illuminate\Foundation\Auth\Access\AuthorizesRequests;

class Controller extends BaseController
{
    use AuthorizesRequests, DispatchesJobs, ValidatesRequests;

    protected function log(Request $request, $msg = null){

        try{
            $log = [
                'user_id' => Auth::check() ? Auth::user()->id : null,
                'description' => $msg ?? null,
                'url' => $request->getRequestUri() ?? null,
                'method' => $request->method() ?? null,
                'route' => Route::currentRouteName() ?? null,
                'ip' => $request->ip() ?? null,
                'agent' => $request->userAgent() ?? null,
                'device_data' => new Agent(),
            ];

            ActivityLogJob::dispatch($log);

        }catch(\Exception $e){
            Log::info('Activity Log Error: ' . $e->getMessage());
        }
    }
}
