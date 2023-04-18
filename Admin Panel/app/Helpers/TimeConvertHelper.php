<?php

namespace App\Helpers;

use Carbon\Carbon;
use Illuminate\Support\Facades\Storage;

class TimeConvertHelper{

    public static function convertTimeZone($date, $from, $to, $format ='24'){
        $date = new \DateTime($date, new \DateTimeZone($from));
        $date->setTimezone(new \DateTimeZone($to));
        if($format == '12'){
            return $date->format('Y-m-d h:i:s A');
        }
        return $date->format('Y-m-d H:i:s');
    }
}
