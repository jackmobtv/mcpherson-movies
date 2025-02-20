document.addEventListener("DOMContentLoaded", () => {
    const themeStitcher = document.getElementById("themingSwitcher");
    const isSystemThemeSetToDark = window.matchMedia("(prefers-color-scheme: dark)").matches;

    // set toggler position based on system theme
    if (isSystemThemeSetToDark) {
        themeStitcher.checked = true;
    }

    const toggleTheme = (isChecked) => {
        const theme = isChecked ? "dark" : "light";
        document.documentElement.dataset.bsTheme = theme;
        console.log("Theme toggled to:", theme);
    }

    // add listener to theme toggler
    themeStitcher.addEventListener("change", (e) => {
        toggleTheme(e.target.checked);
        console.log("Toggle Changed:", e.target.checked);
    });

    // add listener to toggle theme with Shift + D
    document.addEventListener("keydown", (e) => {
        if (e.shiftKey && e.key === "D") {
            themeStitcher.checked = !themeStitcher.checked;
            toggleTheme(themeStitcher.checked);
        }
    });
});