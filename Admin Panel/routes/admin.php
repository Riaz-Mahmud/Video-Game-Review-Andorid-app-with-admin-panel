<?php

use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Backend\HomeController;
use App\Http\Controllers\Backend\Game\GameController;
use App\Http\Controllers\Backend\Role\RoleController;
use App\Http\Controllers\Backend\User\UserController;
use App\Http\Controllers\Backend\Review\ReviewController;
use App\Http\Controllers\Backend\Category\CategoryController;

Route::middleware(['auth', 'verified'])->group(function () {

    Route::get('/', [HomeController::class, 'index']);
    Route::get('/dashboard', [HomeController::class, 'index'])->name('admin.dashboard');

    ///////////////////// Role Start //////////////////////
    Route::prefix('role')->group(function () {
        Route::get('/', [RoleController::class, 'index'])->name('admin.role.index');
        Route::post('/store', [RoleController::class, 'store'])->name('admin.role.store');
        Route::post('/update/{id}', [RoleController::class, 'update'])->name('admin.role.update');
        Route::post('/update/user/role', [RoleController::class, 'updateUserRole'])->name('admin.role.update.user');
        Route::post('/delete/{id}', [RoleController::class, 'delete'])->name('admin.role.delete');
    });
    ///////////////////// Role End //////////////////////

    ///////////////////// User Start //////////////////////
    Route::prefix('user')->group(function () {
        Route::get('/', [UserController::class, 'index'])->name('admin.user.index');
        Route::get('/getAllUsers', [UserController::class, 'getAllUsers'])->name('admin.user.getAllUsers');
        // Route::get('/create', [UserController::class, 'create'])->name('admin.user.create');
        // Route::post('/store', [UserController::class, 'store'])->name('admin.user.store');
        // Route::get('/edit/{id}', [UserController::class, 'edit'])->name('admin.user.edit');
        Route::get('/update/status/{id}', [UserController::class, 'updateStatus'])->name('admin.user.update.status');
        Route::get('/delete/{id}', [UserController::class, 'delete'])->name('admin.user.delete');
    });
    ///////////////////// User End //////////////////////

    ///////////////////// Course Start //////////////////////
    Route::prefix('games')->group(function () {
        Route::get('/', [GameController::class, 'index'])->name('admin.games.index');
        Route::get('/create', [GameController::class, 'create'])->name('admin.games.create');
        Route::post('/store', [GameController::class, 'store'])->name('admin.games.store');
        Route::get('/edit/{id}', [GameController::class, 'edit'])->name('admin.games.edit');
        Route::post('/update/{id}', [GameController::class, 'update'])->name('admin.games.update');
        Route::post('/delete/{id}', [GameController::class, 'destroy'])->name('admin.games.delete');
    });
    ///////////////////// Course End //////////////////////

    ///////////////// Single Game Details Start //////////////////////
    Route::prefix('game')->group(function () {
        Route::get('/', function () { return redirect()->route('home'); });
        Route::get('/{slug}', [GameController::class, 'show'])->name('admin.game.show');
    });
    ///////////////// Single Game Details End //////////////////////

    ///////////////////// Review Start //////////////////////
    Route::prefix('review')->group(function () {
        Route::get('/', [ReviewController::class, 'index'])->name('admin.review.index');
        Route::post('/update/status/{id}', [ReviewController::class, 'updateStatus'])->name('admin.review.update.status');
        Route::post('/delete/{id}', [ReviewController::class, 'destroy'])->name('admin.review.delete');
    });
    ///////////////////// Review End //////////////////////

});

