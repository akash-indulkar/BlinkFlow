
export const HeroVideo = () => {
    return (
  <div className="flex justify-center relative">
    <video
      autoPlay
      muted
      loop
      playsInline
      preload="none"
      src="https://res.cloudinary.com/dadualj4l/video/upload/v1752381445/BlinkFlowCloudStore/original-53dd90f3a61e56b5023f0117283bf0c9_1_fqyj6n.mp4"
      className="max-w-4xl hidden md:block mt-[-100px] absolute -z-10"/>
  </div>
);

}