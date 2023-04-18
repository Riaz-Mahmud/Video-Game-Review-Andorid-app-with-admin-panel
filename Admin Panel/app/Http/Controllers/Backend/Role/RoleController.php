<?php

namespace App\Http\Controllers\Backend\Role;

use App\Models\User;
use App\Helpers\ImageHelper;
use App\Http\Controllers\Backend\BackendController;
use Illuminate\Http\Request;
use Illuminate\Validation\Rule;
use Illuminate\Support\Facades\DB;
use Spatie\Permission\Models\Role;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Support\Facades\Session;
use Illuminate\Support\Facades\Validator;

class RoleController extends BackendController
{
    public function __construct(){
    }

    function index(Request $request){

        $data = [];
        $data['roles'] = Role::all();

        foreach($data['roles'] as $role){

            $role->permissions = $role->permissions()->pluck('name')->toArray();

            $role->hashId = Crypt::encrypt($role->id);
            $role->users = $role->users()->latest()->get();

            foreach($role->users as $user){
                $user->hashId = Crypt::encrypt($user->id);
                $user->image = ImageHelper::getProfileImage($user, 'thumbnail');
            }

            $role->userCount = $role->users()->count();

        }

        parent::log($request , 'View Roles');

        return view('backend.pages.role.index')->with('data', $data);

    }

    function store(Request $request){
        try{

            DB::beginTransaction();

            $validator = Validator::make($request->all(), [
                'name' => 'required|unique:roles|max:100',
            ]);

            if ($validator->fails()) {
                Session::flash('error', $validator->errors()->first());
                return redirect()->back()->withErrors($validator)->withInput();
            }

            $role = Role::create(['name' => $request->name]);
            if(!$role){
                Session::flash('error', 'Role not created');
                return redirect()->back();
            }

            if($request->permissions){
                $role->givePermissionTo($request->permissions);
            }

            parent::log($request , 'Create Role. Role Name: ' . $request->name);

            DB::commit();
            Session::flash('success', 'Role created successfully');
            return redirect()->back();

        }catch(\Exception $e){
            DB::rollback();
            Session::flash('error', 'Something went wrong. ' . $e->getMessage());
            return redirect()->back();
        }
    }

    function update(Request $request, $id){
        try{

            DB::beginTransaction();

            $validator = Validator::make($request->all(), [
                'name' => ['required','string','max:100', Rule::unique('roles')->ignore(Crypt::decrypt($id))],
            ]);

            if ($validator->fails()) {
                Session::flash('error', $validator->errors()->first());
                return redirect()->back()->withErrors($validator)->withInput();
            }

            $role = Role::find(Crypt::decrypt($id));
            if(!$role){
                Session::flash('error', 'Role not found');
                return redirect()->back();
            }

            $role->name = $request->name;
            $update = $role->save();
            if(!$update){
                Session::flash('error', 'Role not updated');
                return redirect()->back();
            }

            if($request->permissions){
                $role->syncPermissions($request->permissions);
            }else{
                $role->syncPermissions([]);
            }

            DB::commit();

            parent::log($request , 'Update Role. Role Name: ' . $request->name . ' Role ID: ' . $role->id);

            Session::flash('success', 'Role updated successfully');
            return redirect()->back();

        }catch(\Exception $e){
            DB::rollback();
            Session::flash('error', 'Something went wrong. ' . $e->getMessage());
            return redirect()->back();
        }
    }

    function delete(Request $request, $id){
        try{

            DB::beginTransaction();

            $role = Role::find(Crypt::decrypt($id));
            if(!$role){
                Session::flash('error', 'Role not found');
                return redirect()->back();
            }

            // if super admin then can not delete
            if($role->id == 1){
                Session::flash('error', 'Super admin role can not be deleted');
                return redirect()->back();
            }

            $delete = $role->delete();
            if(!$delete){
                Session::flash('error', 'Role not deleted');
                return redirect()->back();
            }

            DB::commit();

            parent::log($request , 'Delete Role. Role Name: ' . $role->name . ' Role ID: ' . $role->id);

            Session::flash('success', 'Role deleted successfully');
            return redirect()->back();

        }catch(\Exception $e){
            DB::rollback();
            dd($e->getMessage());
            Session::flash('error', 'Something went wrong. ' . $e->getMessage());
            return redirect()->back();
        }
    }


    function updateUserRole(Request $request){
        try{
            $validator = Validator::make($request->all(), [
                'userId' => 'required',
                'roleId' => 'nullable',
            ]);

            if ($validator->fails()) {
                return response()->json([
                    'status' => 'error',
                    'message' => 'Required fields are missing'
                ]);
            }

            $user = User::find(Crypt::decrypt($request->userId));
            if(!$user){
                return response()->json([
                    'status' => 'error',
                    'message' => 'User not found',
                ]);
            }

            if(!$request->roleId){
                $user->syncRoles([]);
                return response()->json([
                    'status' => 'success',
                    'message' => 'User role updated successfully',
                ]);
            }

            $role = Role::find(Crypt::decrypt($request->roleId));
            if(!$role){
                return response()->json([
                    'status' => 'error',
                    'message' => 'Role not found',
                ]);
            }

            $user->syncRoles($role);

            parent::log($request , 'Update User Role. User Name: ' . $user->name . ' User ID: ' . $user->id . ' Role Name: ' . $role->name . ' Role ID: ' . $role->id);

            return response()->json([
                'status' => 'success',
                'message' => 'User role updated successfully',
            ]);
        }catch(\Exception $e){
            return response()->json([
                'status' => 'error',
                'message' => 'Something went wrong. ' . $e->getMessage(),
            ]);
        }

    }

}
