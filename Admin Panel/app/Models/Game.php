<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Game extends Model
{
    use HasFactory;

    protected $table = 'games';

    protected $fillable = [
        'name',
        'slug',
        'description',
        'banner',
        'status',
        'is_deleted',
        'created_by',
        'updated_by',
        'deleted_by',
    ];

    public function reviews(){
        return $this->hasMany(Review::class);
    }
}
