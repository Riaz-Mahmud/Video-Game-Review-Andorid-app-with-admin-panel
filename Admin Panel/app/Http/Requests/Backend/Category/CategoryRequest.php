<?php

namespace App\Http\Requests\Backend\Category;

use Illuminate\Validation\Rule;
use Illuminate\Support\Facades\Crypt;
use Illuminate\Foundation\Http\FormRequest;

class CategoryRequest extends FormRequest
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
        if($this->route()->getName() == 'admin.category.store'){
            return [
                'title' => 'required|string|max:50|unique:categories',
                'description' => 'nullable|string',
                'status' => 'required|in:Active,Inactive',
            ];
        }
        if($this->route()->getName() == 'admin.category.update'){
            return [
                'title' => ['required','string','max:50', Rule::unique('categories')->ignore(Crypt::decrypt($this->id))],
                'description' => 'nullable|string',
                'status' => 'required|in:Active,Inactive',
            ];
        }
    }
}
