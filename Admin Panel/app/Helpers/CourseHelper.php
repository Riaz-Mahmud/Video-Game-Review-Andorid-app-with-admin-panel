<?php

namespace App\Helpers;

use Carbon\Carbon;
use Illuminate\Support\Facades\Storage;

class CourseHelper{

    public static function price($course){
        return self::calculatePriceprice($course)->showing_price;
    }

    protected static function calculatePriceprice($course){
        $data['course'] = $course;
        if($data['course']->sale_expiry_date){
            $sale_expiry_date = Carbon::parse($data['course']->sale_expiry_date)->format('Y-m-d H:i:s');
            $data['course']->price_html = ($sale_expiry_date > Carbon::now()->format('Y-m-d H:i:s')) ? '৳'.number_format($data['course']->sale_price, 2).'<small> <del> ৳'.number_format($data['course']->price, 2).'</del></small>' : '৳'.number_format($data['course']->price, 2);
            $data['course']->showing_price = ($sale_expiry_date > Carbon::now()->format('Y-m-d H:i:s')) ? $data['course']->sale_price : $data['course']->price;
        }else{
            $data['course']->price_html = '৳'.number_format($data['course']->price, 2);
            $data['course']->showing_price = $data['course']->price;
        }
        return $data['course'];
    }


    public static function priceHtml($course){
        return self::calculatePriceprice($course)->price_html;
    }

}
