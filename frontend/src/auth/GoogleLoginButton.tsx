const googleLogin = () => {
  window.location.href = "http://localhost:8080/oauth2/authorization/google";
};

export const GoogleLoginButton = () => {
  return <div onClick={googleLogin} className="flex bg-blue-500 hover:bg-blue-600 text-center justify-center items-center text-white font-semibold py-2  rounded w-full cursor-pointer">
     <img className="bg-white p-1 h-8 w-8 rounded" src="https://res.cloudinary.com/dadualj4l/image/upload/v1752993995/g_pvjvub.webp"></img>
    <span className="font-sans ml-[68px] mr-[90px]">Continue with Google</span>
  </div>
}
