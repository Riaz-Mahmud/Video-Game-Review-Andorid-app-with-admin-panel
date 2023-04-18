<?php

namespace App\Http\Controllers\Backend\User;

use App\Helpers\ImageHelper;
use App\Models\Profile;
use Illuminate\Http\Request;
use Spatie\Permission\Models\Role;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Crypt;
use App\Http\Controllers\Backend\BackendController;

class UserController extends BackendController
{
    function index( Request $request ){

        $data = [];

        // get all the role except the super admin
        $data['roles'] = Role::where('name', '!=', config('backend.role')[0])->get();

        $user = Auth::user();
        $data['user'] = $user;
        $data['permissions'] = $user->getAllPermissions()->pluck('name')->toArray();

        foreach ($data['roles'] as $role) {
            $role->hashId = Crypt::encrypt($role->id);
        }

        $profiles = Profile::where('is_deleted', 0)->get();
        foreach ($profiles as $profile) {

            $data['users'][] = [
                'hashId' => Crypt::encrypt($profile->user_id),
                'full_name' => $profile->first_name . ' ' . $profile->last_name,
                'role' => $profile->user->getRoleNames()->first() ?? null,
                'phone' => $profile->phone,
                'email' => $profile->email,
                'status' => $profile->status,
                'image' => ImageHelper::getProfileImage($profile->user,'main'),
                'permission' => Auth::user()->hasPermissionTo('admin.user.assign.role') ? true : false,
            ];
        }

        return view('backend.pages.user.index')->with('data', $data);
    }

    function delete( Request $request, $id ){

        try{
            $id = Crypt::decrypt($id);

            $profile = Profile::where('user_id', $id)->first();
            $profile->is_deleted = 1;
            $profile->save();

            Session()->flash('success', 'User deleted successfully');
            return redirect()->route('admin.user.index');

        }catch(\Exception $e){
            Session()->flash('error', 'Something went wrong');
            return redirect()->route('admin.user.index');
        }
    }

}
