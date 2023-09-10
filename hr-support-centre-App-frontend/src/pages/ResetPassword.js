import React, { useState } from 'react'

const ResetPassword = () => {
  return (
    <div className="flex flex-col items-center">
      <div>
        <h1 className="font-bold text-2xl mb-24 text-center mt-20">HR Support Center</h1>
      </div>
      <div className="bg-white h-351 w-451 p-4">
        <h1 className="font-bold text-2xl mb-2">Reset Password</h1>
        <div>
          <form>
          <div className="form-group mb-2">
                <label htmlFor="new-password" className="col-form-label">
                  New Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="new-password"
                  name="new-password"
                  placeholder="Password"
                />
              </div>

              <div className="form-group mb-2">
                <label htmlFor="confirm-password" className="col-form-label">
                  Confirm Password
                </label>
                <input
                  type="password"
                  className="form-control form-control-user"
                  id="confirm-password"
                  name="confirm-password"
                  placeholder="Password"
                />
              </div>

            <button className="rounded-md h-44 px-6 font-bold text-white bg-primaryColor btn-block w-full mt-10">
              Reset Password
            </button>
          </form>
        </div>
      </div>
    </div>
  )
}

export default ResetPassword
