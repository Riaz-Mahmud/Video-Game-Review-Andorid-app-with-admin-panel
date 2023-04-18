<?php

namespace App\Helpers;

use Illuminate\Support\Facades\Storage;

class ImageHelper{

    public static function generateImage($path, $type = 'main'){
        if($path == null || $path == ''){
            return Storage::url('assets/frontend/img/404.png');
        }
        if($type == 'main'){
            if(file_exists(storage_path('app/public/'.$path))){
                return Storage::url($path);
            }else{
                return Storage::url('assets/frontend/img/404.png');
            }
        }else{
            $file_base_name = basename($path);
            $file_extension = pathinfo($file_base_name, PATHINFO_EXTENSION);
            $file_name_with_url = str_replace('.'.$file_extension, '', $path);

            $newFile = $file_name_with_url.'_'.$type.'.'.$file_extension;

            if(file_exists(storage_path('app/public/'.$newFile))){
                return Storage::url($newFile);
            }
            return Storage::url('assets/frontend/img/404.png');
        }
    }

    public static function getProfileImage($user, $type = 'main'){
        if(!$user->profile){
            return 'https://ui-avatars.com/api/?name=' . $user->first_name . '+' . $user->last_name . '&background=0D8ABC&color=fff';
        }
        if($user->profile->picture == null || $user->profile->picture == ''){
            return 'https://ui-avatars.com/api/?name=' . $user->first_name . '+' . $user->last_name . '&background=0D8ABC&color=fff';
        }
        $path = $user->profile->picture;
        if($type == 'main'){
            if(file_exists(storage_path('app/public/'.$path))){
                return Storage::url($path);
            }else{
                return 'https://ui-avatars.com/api/?name=' . $user->first_name . '+' . $user->last_name . '&background=0D8ABC&color=fff';
            }
        }else{
            $file_base_name = basename($path);
            $file_extension = pathinfo($file_base_name, PATHINFO_EXTENSION);
            $file_name_with_url = str_replace('.'.$file_extension, '', $path);

            $newFile = $file_name_with_url.'_'.$type.'.'.$file_extension;

            if(file_exists(storage_path('app/public/'.$newFile))){
                return Storage::url($newFile);
            }
            return 'https://ui-avatars.com/api/?name=' . $user->first_name . '+' . $user->last_name . '&background=0D8ABC&color=fff';
        }
    }

    public static function generateVideo($path, $type = 'main'){
        if($path == null || $path == ''){
            return Storage::url('assets/frontend/img/404.png');
        }
        if($type == 'main'){
            return Storage::url($path);
        }else{
            $file_base_name = basename($path);
            $file_extension = pathinfo($file_base_name, PATHINFO_EXTENSION);
            $file_name_with_url = str_replace('.'.$file_extension, '', $path);

            $newFile = $file_name_with_url.'_'.$type.'.'.$file_extension;

            if(file_exists(storage_path('app/public/'.$newFile))){
                return Storage::url($newFile);
            }
            return Storage::url('assets/frontend/img/404.png');
        }
    }

}
