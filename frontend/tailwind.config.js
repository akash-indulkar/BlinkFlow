export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      backgroundImage: {
        "gradient-radial": "radial-gradient(var(--tw-gradient-stops))",
        "gradient-conic":
          "conic-gradient(from 180deg at 50% 50%, var(--tw-gradient-stops))",
      },
      colors: {
        amber: {
          700: "#ff4f00"
        },
        slate: {
          100: "#ebe9df"
        }
      },
      animation: {
        "infinite-scroll": "infiniteScroll 20s linear infinite",
      },
      keyframes: {
        infiniteScroll: {
          "0%": { transform: "translateX(0)" },
          "100%": { transform: "translateX(calc(-50% - 32px))" }
        }
      },
      fontFamily: {
        gothic: ['Special Gothic Expanded One', 'sans-serif'],
        opensans: ['Open Sans', 'sans-serif'],
        merriweather: ['Merriweather', 'sans-serif'],
      }
    },
  },
  plugins: [],
} 

