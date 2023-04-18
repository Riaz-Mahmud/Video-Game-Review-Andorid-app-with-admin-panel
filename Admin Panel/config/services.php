<?php

return [

    /*
    |--------------------------------------------------------------------------
    | Third Party Services
    |--------------------------------------------------------------------------
    |
    | This file is for storing the credentials for third party services such
    | as Mailgun, Postmark, AWS and more. This file provides the de facto
    | location for this type of information, allowing packages to have
    | a conventional file to locate the various service credentials.
    |
    */

    'mailgun' => [
        'domain' => env('MAILGUN_DOMAIN'),
        'secret' => env('MAILGUN_SECRET'),
        'endpoint' => env('MAILGUN_ENDPOINT', 'api.mailgun.net'),
        'scheme' => 'https',
    ],

    'postmark' => [
        'token' => env('POSTMARK_TOKEN'),
    ],

    'ses' => [
        'key' => env('AWS_ACCESS_KEY_ID'),
        'secret' => env('AWS_SECRET_ACCESS_KEY'),
        'region' => env('AWS_DEFAULT_REGION', 'us-east-1'),
    ],

    /*
    |--------------------------------------------------------------------------
    | geloky API Key
    |--------------------------------------------------------------------------
    |
    | This is use for getting the coordinates of the user's address.
    | When the user registers, we get the coordinates of the address and save it in the database.
    | When the user updates the address, we get the coordinates of the address and update it in the database.
    |
    */

    'geloky' => [
        'api_key' => env('GELOKY_API_KEY', 'jAhDaBZ7koWLsUSLp6rX6pOEXfl9WAm1'),
    ],

    /*
    |--------------------------------------------------------------------------
    | Google Maps API Key
    |--------------------------------------------------------------------------
    |
    | This is use for showing Peers Near Me on the map.
    |
    */

    'googleMaps' => [
        'api_key' => env('GOOGLE_MAPS_API_KEY', 'AIzaSyDugIMfiX1Sa9fVahIn52fRhr1Coa7pfeM'),
    ],

];
