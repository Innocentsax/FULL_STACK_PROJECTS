import React from 'react'

const AdminHrSettings = () => {
  return (
    <div className="flex flex-col">
      <div className="flex flex-col items-center my-4">
      <div className="justify-center flex items-center w-880 h-61 bg-primaryColor rounded-2xl">
        <h1 className="font-bold text-2xl text-white">Settings</h1>
      </div>
      </div>
      <div className="row">
        <div className="d-none d-md-block col-lg-4 text-right">
          <button className="bg-white w-36 h-14 text-primaryColor">
            Change Password
          </button>
        </div>
        <div className="col-8 border-l-4 m-0">
          <div className="mt-32 ml-16 ">
            <h1 className="mb-8 font-bold text-2xl">Change Password</h1>
            <div className="bg-white w-478 h-404 px-8 py-3">
              <div className="form-group mb-2">
                <label
                  htmlFor="old-password"
                  className="font-medium col-form-label"
                >
                  Old Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="old-password"
                  name="old-password"
                  placeholder="Type in old Password"
                />
              </div>

              <div className="form-group  mb-2">
                <label
                  htmlFor="new-password"
                  className="font-medium col-form-label"
                >
                  New Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="new-password"
                  name="new-password"
                  placeholder="Type in new Password"
                />
              </div>

              <div className="form-group">
                <label
                  htmlFor="confirm-password"
                  className="font-medium col-form-label"
                >
                  Confirm New Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="confirm-password"
                  name="confirm-password"
                  placeholder="Confirm Password"
                />
              </div>

              <div>
                <button className="mt-4 rounded-md text-white h-44 bg-primaryColor btn-block w-full">
                  Save Change
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default AdminHrSettings
