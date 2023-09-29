import "./ForgottenPassword.css";
import React, { useState } from "react";
import { useAuth } from "../../context/authcontext";
import Loader from "../../components/Loader/Loader";

const Forgotten = () => {
  const { ForgottenConfig } = useAuth();
  const [user, setUser] = useState({
    email: "",
  });

   const [isLoading, setIsLoading] = useState(false);

  const handleChange = (e) => {
    const value = e.target.value;
    setUser({...user, [e.target.name]: value })
  };

    const handleSubmit = async (e) => {
      e.preventDefault();
      setIsLoading(true);
      await ForgottenConfig(user);
      setIsLoading(false);
      setUser({
        
        email: "",
        
      });
    };

  return (
    <div className="forgotten_bg_image">
      <form
        // id="form"
        className="forgottenForm"
        onSubmit={handleSubmit}
      >
        <h2 className="forgotten_h3">Forgot your password?</h2>

        <input
          type="email"
          name="email"
          value={user.email}
          onChange={handleChange}
          //  {...register("email")}
          placeholder="Email"
          required
        />

        <button className="forgotten_btn" type="submit">
          Reset my password
        </button>

        <p className="forgotten_small mb-0">
          Don't have an account ?<a href="/signup">Sign up</a>
        </p>
        <p className="forgotten_small mb-0">
          Already have an account ? <a href="/login">Login</a>
        </p>
      </form>
      {isLoading && <Loader />}
    </div>
  );
};

export default Forgotten;
