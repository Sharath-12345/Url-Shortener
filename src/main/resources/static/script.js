const API_BASE_URL = ApiConfig.baseUrl;

async function shortenUrl() {
    const longUrl = document.getElementById("longUrl").value;

    if (!longUrl) {
        alert("Please enter a URL");
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/api/shorten`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ longUrl })
        });

        if (!response.ok) {
            throw new Error("Failed to shorten URL");
        }

        const data = await response.json();

        const shortUrlElement = document.getElementById("shortUrl");
        shortUrlElement.href = data.shortUrl;
        shortUrlElement.innerText = data.shortUrl;

        document.getElementById("result").classList.remove("hidden");

    } catch (error) {
        alert("Something went wrong");
        console.error(error);
    }
}

function copyUrl() {
    const shortUrl = document.getElementById("shortUrl").innerText;
    navigator.clipboard.writeText(shortUrl);
    alert("Copied to clipboard!");
}
