/** @type {import('tailwindcss').Config} */
module.exports = {
    purge: {
        enabled: true,
        content: ["../templates/thymeleaf/**/*.html"],
      },
  theme: {
    extend: {},
  },
  plugins: [],
}

