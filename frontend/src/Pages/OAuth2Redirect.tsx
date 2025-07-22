import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../auth/AuthContext";
import toast from "react-hot-toast";

export default function OAuth2Redirect() {

  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    const error = urlParams.get('error');
    if (token) {
      login(token)
      toast.success("Welcome to the dashboard!")
      navigate("/dashboard")
    }
    if (error) {
      toast.error("Invalid credentials")
      navigate("/login/redirect")
    }
  }, []);
  return <div></div>
}
