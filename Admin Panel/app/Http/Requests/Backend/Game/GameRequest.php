<?php

namespace App\Http\Requests\Backend\Game;

use Illuminate\Support\Facades\Crypt;
use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;


class GameRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, mixed>
     */
    public function rules()
    {
        if ($this->route()->getName() == 'admin.games.store') {
            return [
                'image' => 'required|image|mimes:jpeg,png,jpg,gif,svg|max:2048',
                'name' => 'required|unique:games',
                'description' => 'nullable',
                'status' => 'required|in:Pending,Active,Inactive',
            ];
        }elseif ($this->route()->getName() == 'admin.games.update') {
            return [
                'image' => 'nullable|image|mimes:jpeg,png,jpg,gif,svg|max:2048',
                'name' => ['required','string','max:180', Rule::unique('games')->ignore(Crypt::decrypt($this->id))],
                'description' => 'nullable',
                'status' => 'required|in:Pending,Active,Inactive',
            ];
        }
    }
}
