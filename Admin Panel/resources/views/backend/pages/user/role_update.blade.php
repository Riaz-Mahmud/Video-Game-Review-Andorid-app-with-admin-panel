<!-- Add New Credit Card Modal -->
<div class="modal fade" id="upgradeRoleModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Default Modal</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center mb-4">
                    <h3>Upgrade Role</h3>
                    <p>Choose the best role for user.</p>
                </div>
                <div class="row g-3">
                    <input type="hidden" name="userId" id="upgradeRoleModalUserId" value="">
                    <div class="col-sm-12">
                        <label class="form-label" for="chooseRole">Choose Role</label>
                        <select id="chooseRole" name="chooseRole" class="form-control" aria-label="Choose Role">
                        </select>
                    </div>
                </div>
            </div>
            <hr class="mx-md-n5 mx-n3">
            <div class="modal-body">
                <h6 class="mb-0">You can set the permission from <a href="{{ route('admin.role.index') }}">Role
                        Management</a></h6>
            </div>
            <div class="modal-footer justify-content-between">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" id="upgradeRoleBtn" class="btn btn-primary">Save changes</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!--/ Add New Credit Card Modal -->
