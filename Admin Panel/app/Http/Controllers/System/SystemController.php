<?php

namespace App\Http\Controllers\System;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Artisan;

class SystemController extends Controller
{
    function storageLink(){
        Artisan::call('storage:link');
        return redirect()->back()->with('success', 'Storage link created successfully. '. Artisan::output());
    }

    function clearCache(){
        Artisan::call('cache:clear');
        Artisan::call('route:clear');
        Artisan::call('view:clear');
        Artisan::call('optimize:clear');
        return redirect()->back()->with('success', 'Cache cleared successfully. ' . Artisan::output());
    }

    function migrate(){
        Artisan::call('migrate');
        return redirect()->back()->with('success', 'Migrate successfully');
    }

    function migrateFresh(){
        return redirect()->back()->with('error', 'Enable from controller');
        Artisan::call('migrate:fresh');
        return redirect()->back()->with('success', 'Migrate fresh successfully');
    }

    function keyGenerate(){
        Artisan::call('key:generate');
        return redirect()->back()->with('success', 'Key generate successfully');
    }

    function composerInstall(){
        Artisan::call('composer install');
        return redirect()->back()->with('success', 'Composer install successfully');
    }

    function composerUpdate(){
        Artisan::call('composer update');
        return redirect()->back()->with('success', 'Composer update successfully');
    }

    function composerDump(){
        Artisan::call('composer dump-autoload');
        return redirect()->back()->with('success', 'Composer dump successfully');
    }

}
