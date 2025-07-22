/** @type {import('tailwindcss').Config} */
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
        'loader-bar': 'loaderBarAnim 1.5s infinite linear'
      },
      keyframes: {
        loaderBarAnim: {
          '0%': { width: '0%', left: '100%' },
          '50%': { width: '70%', left: '30%' },
          '100%': { width: '100%', left: '0%' },
        }
      }
    },
  },
  plugins: [],
}

