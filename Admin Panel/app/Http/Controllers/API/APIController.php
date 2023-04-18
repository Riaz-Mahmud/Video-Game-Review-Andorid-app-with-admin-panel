<?php

namespace App\Http\Controllers\API;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;

class APIController extends Controller
{
    protected function apiResponse($success, $error, $result =null){
        return response()->json([
            'success' => $success,
            'error' => $error ?? null,
            'result' => $result ?? null,
        ]);
    }
}
