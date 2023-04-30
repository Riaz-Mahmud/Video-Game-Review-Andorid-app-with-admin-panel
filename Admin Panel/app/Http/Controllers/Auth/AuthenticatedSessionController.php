<?php

namespace App\Http\Controllers\Auth;

use App\Models\User;
use Illuminate\View\View;
use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Http\RedirectResponse;
use App\Providers\RouteServiceProvider;
use Illuminate\Support\Facades\Session;
use App\Http\Requests\Auth\LoginRequest;

class AuthenticatedSessionController extends Controller
{
    /**
     * Display the login view.
     */
    public function create()
    {
        if (Auth::guard('web')->check()) {
            return redirect()->intended(RouteServiceProvider::HOME_ADMIN);
        }

        return view('backend.pages.auth.login');
    }

    /**
     * Handle an incoming authentication request.
     */
    public function store(LoginRequest $request): RedirectResponse
    {
        $request->authenticate();

        $request->session()->regenerate();

        // check the user role
        // only admin can access admin panel
        if (Auth::guard('web')->user()->getRoleNames()->first() == 'Admin' || Auth::guard('web')->user()->getRoleNames()->first() == 'Super Admin') {
            return redirect()->intended(RouteServiceProvider::HOME_ADMIN);
        }else{
            // if user is not admin then logout the user
            Session::flash('error', 'You are not authorized to access admin panel.');
            Auth::guard('web')->logout();
            return redirect()->route('login');
        }
    }

    /**
     * Destroy an authenticated session.
     */
    public function destroy(Request $request): RedirectResponse
    {
        Auth::guard('web')->logout();

        $request->session()->invalidate();

        $request->session()->regenerateToken();

        return redirect('/');
    }
}
