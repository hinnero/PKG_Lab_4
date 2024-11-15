const form = document.getElementById("raster-form");
const algorithmSelect = document.getElementById("algorithm");
const lineInputs = document.getElementById("line-inputs");
const circleInputs = document.getElementById("circle-inputs");
const canvas = document.getElementById("canvas");
const ctx = canvas.getContext("2d");

const API_BASE = "http://localhost:8080/api/raster";

function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = "#fff";
    ctx.fillRect(0, 0, canvas.width, canvas.height);
}

function drawPoints(points) {
    clearCanvas();
    ctx.fillStyle = "#0077ff";
    points.forEach(({ x, y }) => {
        ctx.fillRect(x, canvas.height - y, 3, 3);
    });
}

algorithmSelect.addEventListener("change", () => {
    if (algorithmSelect.value === "bresenhamCircle") {
        lineInputs.classList.add("hidden");
        circleInputs.classList.remove("hidden");

        document.getElementById("x0").required = false;
        document.getElementById("y0").required = false;
        document.getElementById("x1").required = false;
        document.getElementById("y1").required = false;

        document.getElementById("cx").required = true;
        document.getElementById("cy").required = true;
        document.getElementById("radius").required = true;
    } else {
        lineInputs.classList.remove("hidden");
        circleInputs.classList.add("hidden");

        document.getElementById("cx").required = false;
        document.getElementById("cy").required = false;
        document.getElementById("radius").required = false;

        document.getElementById("x0").required = true;
        document.getElementById("y0").required = true;
        document.getElementById("x1").required = true;
        document.getElementById("y1").required = true;
    }
});

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const algorithm = algorithmSelect.value;
    const payload =
        algorithm === "bresenhamCircle"
            ? {
                x0: +document.getElementById("cx").value,
                y0: +document.getElementById("cy").value,
                radius: +document.getElementById("radius").value,
            }
            : {
                x0: +document.getElementById("x0").value,
                y0: +document.getElementById("y0").value,
                x1: +document.getElementById("x1").value,
                y1: +document.getElementById("y1").value,
            };

    const endpoint =
        algorithm === "dda"
            ? "/dda"
            : algorithm === "cda"
                ? "/cda"
                : algorithm === "bresenhamLine"
                    ? "/bresenham/line"
                    : "/bresenham/circle";

    try {
        const response = await fetch(`${API_BASE}${endpoint}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(payload),
        });

        const data = await response.json();
        drawPoints(data.points);
    } catch (error) {
        alert("An error occurred: " + error.message);
    }
});

clearCanvas();
