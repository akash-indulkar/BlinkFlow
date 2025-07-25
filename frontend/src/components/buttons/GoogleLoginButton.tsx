
const googleLogin = () => {
  const GOOGLE_AUTH_URL = import.meta.env.VITE_GOOGLE_AUTH_URL;
  window.location.href = GOOGLE_AUTH_URL;
};

export const GoogleLoginButton = () => {
  return (
    <div
      onClick={googleLogin}
      className="flex bg-blue-500 hover:bg-blue-600 items-center justify-center text-white font-semibold py-2 rounded w-full cursor-pointer gap-4">
      <img
        className="bg-white p-1 h-8 w-8 rounded"
        src="https://res.cloudinary.com/dadualj4l/image/upload/v1752993995/g_pvjvub.webp"
        alt="Google"/>
      <span className="font-sans text-sm sm:text-base">Continue with Google</span>
    </div>
  );

}
