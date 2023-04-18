<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\API\Game\GameController;
use App\Http\Controllers\API\User\UserController;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::post('/login', [UserController::class, 'login'])->name('api.v1.login');
Route::post('/logout', [UserController::class, 'logout'])->name('api.v1.logout');
Route::post('/me', [UserController::class, 'me'])->name('api.v1.me');
Route::post('/registration', [UserController::class, 'registration'])->name('api.v1.user.registration');

Route::middleware('auth:api')->group(function(){

    //version 1 routes
    Route::prefix('v1')->group(function () {

        Route::prefix('user')->group(function () {

            Route::get('profile', [UserController::class, 'profile'])->name('api.v1.user.profile');
            Route::post('checkstatus', [UserController::class, 'CheckUserStatus'])->name('api.v1.user.checkstatus');
        });

        //////////////////// Games Start ////////////////////
        Route::prefix('games')->group(function () {

            Route::get('list', [GameController::class, 'list'])->name('api.v1.games.list');
            Route::get('list/{id}', [GameController::class, 'details'])->name('api.v1.games.details');

            //////////////////// Reviews Start ////////////////////
            Route::prefix('reviews')->group(function () {
                Route::get('list', [GameController::class, 'reviewsList'])->name('api.v1.games.reviews.list');
                Route::get('list/{id}', [GameController::class, 'reviewsDetails'])->name('api.v1.games.reviews.details');
                Route::post('add', [GameController::class, 'reviewsAdd'])->name('api.v1.games.reviews.add');
            });
            //////////////////// Reviews End ////////////////////
        });
        //////////////////// Games End ////////////////////

    });

});
