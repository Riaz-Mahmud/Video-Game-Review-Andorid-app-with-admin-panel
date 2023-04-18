<!-- Add Role Modal -->
<div class="modal fade" id="addRoleModal" tabindex="-1" aria-hidden="true" style="overflow-x: hidden;">
    <div class="modal-dialog modal-lg modal-simple modal-dialog-centered modal-add-new-role">
        <div class="modal-content p-3 p-md-4">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center mb-4">
                    <h3 class="role-title">Add New Role</h3>
                    <p>Set role permissions</p>
                </div>
                <!-- Add role form -->
                <form id="addRoleForm" class="row g-3 addRoleForm" action="{{ route('admin.role.store') }}"
                    method="POST">
                    @csrf
                    <div class="col-12 mb-4">
                        <label class="form-label" for="modalRoleName">Role Name</label>
                        <input type="text" id="modalRoleName" name="name" class="form-control"
                            placeholder="Enter a role name" tabindex="-1" />
                    </div>
                    @can('admin.role.assign.permission')
                        <div class="col-12">
                            <h4>Role Permissions</h4>
                            <!-- Permission table -->
                            <div class="table-responsive">
                                <table class="table table-flush-spacing">
                                    <tbody>
                                        <tr>
                                            <td class="text-nowrap fw-semibold">Administrator Access <i
                                                    class="bx bx-info-circle bx-xs" data-bs-toggle="tooltip"
                                                    data-bs-placement="top" title="Allows a full access to the system"></i>
                                            </td>
                                            <td>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" id="selectAll" />
                                                    <label class="form-check-label" for="selectAll">
                                                        Select All
                                                    </label>
                                                </div>
                                            </td>
                                        </tr>
                                        @foreach (config('backend.permission') as $key => $permissions)
                                            <tr>
                                                <td class="text-nowrap fw-semibold">{{ $key }}</td>
                                                <td>
                                                    <div class="d-flex">
                                                        @foreach ($permissions as $key1 => $permission)
                                                            <div class="form-check me-3 me-lg-5">
                                                                <input class="form-check-input" type="checkbox"
                                                                    name="permissions[]" value="{{ $permission }}"
                                                                    id="{{ $permission }}{{ $key1 }}" />
                                                                <label class="form-check-label"
                                                                    for="{{ $permission }}{{ $key1 }}">
                                                                    {{ $key1 }}
                                                                </label>
                                                            </div>
                                                        @endforeach
                                                    </div>
                                                </td>
                                            </tr>
                                        @endforeach
                                    </tbody>
                                </table>
                            </div>
                            <!-- Permission table -->
                        </div>
                    @endcan
                    <div class="col-12 text-center">
                        <button type="submit" class="btn btn-primary me-sm-3 me-1">Submit</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
                <!--/ Add role form -->
            </div>
        </div>
    </div>
</div>
<!--/ Add Role Modal -->

