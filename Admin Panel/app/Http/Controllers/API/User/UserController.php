<?php

namespace App\Http\Controllers\API\User;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use App\Http\Controllers\API\APIController;
use App\Models\Profile;

class UserController extends APIController
{
    public function __construct(){

        $this->middleware('auth:api', ['except' => ['login' , 'registration', 'checkUserStatus']]);

    }

    function registration(Request $request){

        $validator = Validator::make($request->all(),
            [
                'first_name' => 'required|string|max:255',
                'last_name' => 'required|string|max:255',
                'username' => 'required|string|max:255|unique:profiles',
                'email' => 'required|string|email|max:255|unique:users',
                'password' => 'required|string|min:8',
            ]);
        if ($validator->fails()) {
            return parent::apiResponse(false, $validator->errors()->first(), null);
        }

        DB::beginTransaction();

        try{
            $user = User::create([
                'full_name' => $request->first_name.' '.$request->last_name,
                'email' => $request->email,
                'email_verified_at' => now(),
                'password' => Hash::make($request->password),
            ]);

            $user->profile()->create([
                'first_name' => $request->first_name,
                'last_name' => $request->last_name,
                'username' => $request->username,
                'email' => $request->email,
                'status' => 'Active',
            ]);

            $token = auth('api')->login($user);

            $user->assignRole('User');

            DB::commit();

            return parent::apiResponse(true, null, [
                'user' => auth('api')->user()->profile,
                'access_token' => $token,
                'token_type' => 'bearer',
                'expires_in' => auth('api')->factory()->getTTL() * 60
            ]);

        }catch(\Exception $e){
            DB::rollBack();
            return parent::apiResponse(false, $e->getMessage(), null);
        }
    }

    function login(Request $request){

        $credentials = request(['email', 'password']);

        if (! $token = auth('api')->attempt($credentials)) {
            return parent::apiResponse(false, 'Unauthorized', null);
        }

        return parent::apiResponse(true, null, [
            'user' => auth('api')->user()->profile,
            'access_token' => $token,
            'token_type' => 'bearer',
            'expires_in' => auth('api')->factory()->getTTL() * 60
        ]);

    }

    public function logout(){
        auth('api')->logout();

        return parent::apiResponse(true, 'Successfully logged out', null);
    }

    /**
     * Refresh a token.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function refresh(){
        return $this->respondWithToken(auth('api')->refresh());
    }

    /**
     * Get the authenticated User.
     *
     * @return \Illuminate\Http\JsonResponse
     */
    public function me(){
        return parent::apiResponse(true, null, ['user' => auth('api')->user()]);
    }

    public function checkUserStatus(Request $request){

        $validator = Validator::make($request->all(),
            [
                'user_id' => 'required',
            ]);
        if ($validator->fails()) {
            return parent::apiResponse(false, 'user id: '. $request->user_id, null);
        } else {
            $user = Profile::where('id', $request->user_id)->where('status', 'Active')->where('is_deleted', 0)->first();
            if ($user) {
                return parent::apiResponse(true, null, ['user' => $user]);
            } else {
                return parent::apiResponse(false, 'User not found', null);
            }
        }
    }
}
