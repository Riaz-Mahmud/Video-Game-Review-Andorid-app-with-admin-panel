<?php

namespace App\Models;

use Carbon\Carbon;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Session as SessionFacade;
use Illuminate\Database\Eloquent\Builder;
use Cartalyst\Sentinel\Laravel\Facades\Sentinel;
use Illuminate\Database\Eloquent\Factories\HasFactory;

class Session extends Model
{
    use HasFactory;

    /**
     * {@inheritdoc}
     */
    public $table = 'sessions';

    /**
     * {@inheritdoc}
     */
    public $timestamps = false;

    /**
     * Returns the user that belongs to this entry.
     *
     * @return \Cartalyst\Sentinel\Users\EloquentUser
     */
    public function user()
    {
        return $this->belongsTo('App\Models\User');
    }

    /**
     * Returns all the users within the given activity.
     *
     * @param  \Illuminate\Database\Eloquent\Builder  $query
     * @param  int  $limit
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function scopeActivity($query, $limit = 10)
    {
        $lastActivity = strtotime(Carbon::now()->subMinutes($limit));

        return $query->where('last_activity', '>=', $lastActivity);
    }

    /**
     * Returns all the guest users.
     *
     * @param  \Illuminate\Database\Eloquent\Builder  $query
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function scopeGuests(Builder $query)
    {
        return $query->whereNull('user_id');
    }

    /**
     * Returns all the registered users.
     *
     * @param  \Illuminate\Database\Eloquent\Builder  $query
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function scopeRegistered(Builder $query)
    {
        return $query->whereNotNull('user_id')->with('user');
    }

    /**
     * Updates the session of the current user.
     *
     * @param  \Illuminate\Database\Eloquent\Builder  $query
     * @return \Illuminate\Database\Eloquent\Builder
     */
    public function scopeUpdateCurrent(Builder $query)
    {
        $user = Sentinel::getUser();

        return $query->where('id', SessionFacade::getId())->update([
            'user_id' => $user ? $user->id : null
        ]);
    }
}
