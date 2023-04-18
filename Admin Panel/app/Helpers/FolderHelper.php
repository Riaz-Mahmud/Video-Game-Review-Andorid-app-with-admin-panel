<?php

namespace App\Helpers;

use Illuminate\Support\Str;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Storage;

class FolderHelper{

    public static function generateFolder(){
        $folder = Str::Upper(Str::random(5).date('YmdHis'));
        $path = config('backend.temp_folder.lecture.path').$folder;
        if(!Storage::exists($path)){
            Storage::makeDirectory($path, 0755);
        }
        return $folder;
    }

    public static function sessionHasKey($type){
        if($type == 'lecture'){
            return Session::has(config('backend.temp_folder.lecture.session_key')) ? true : false;
        }
    }

    public static function folderExists($type, $folder){
        if($type == 'lecture'){
            $path = config('backend.temp_folder.lecture.path').$folder;
            return Storage::exists($path) ? true : false;
        }
    }

}
