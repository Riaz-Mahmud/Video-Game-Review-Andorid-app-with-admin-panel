<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\System\SystemController;

Route::prefix('system')->middleware(['auth'])->group(function () {

    Route::get('/storagelink', [SystemController::class, 'storageLink'])->name('admin.system.storage-link');
    Route::get('/clearcache', [SystemController::class, 'clearCache'])->name('admin.system.clear-cache');
    Route::get('/migrate', [SystemController::class, 'migrate'])->name('admin.system.migrate');
    Route::get('/migrate_fresh', [SystemController::class, 'migrateFresh'])->name('admin.system.migrate-fresh');
    Route::get('/key_generate', [SystemController::class, 'keyGenerate'])->name('admin.system.key-generate');
    Route::get('/composer_install', [SystemController::class, 'composerInstall'])->name('admin.system.composer-install');
    Route::get('/composer_update', [SystemController::class, 'composerUpdate'])->name('admin.system.composer-update');
    Route::get('/composer_dump_autoload', [SystemController::class, 'composerDump'])->name('admin.system.composer-dump-autoload');

});

