<?php

namespace App\Helpers;


use Illuminate\Support\Str;

class StringHelper{

    public static function title($title){
        return Str::limit(($title ?? ''), 80 , '...');
    }

    public static function desc($description, $limit = 100){
        return Str::limit(($description ?? ''), $limit , '...');
    }

}
