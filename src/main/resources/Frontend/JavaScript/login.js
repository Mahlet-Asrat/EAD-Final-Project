document.getElementById("login-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const errorMessage = document.getElementById("error-message");

    // Clear any previous error messages
    errorMessage.textContent = "";
    errorMessage.classList.add("d-none");

    try {
        console.log("Attempting login with:", { email });

        const response = await fetch("http://localhost:8080/api/v1/users/login", {
            method: "POST",
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        console.log("Response status:", response.status);
        console.log("Response headers:", Object.fromEntries(response.headers.entries()));

        // Handle non-200 responses
        if (!response.ok) {
            const errorText = await response.text();
            console.error("Error response:", errorText);
            throw new Error(errorText || "Login failed. Please check your credentials.");
        }

        // Parse the JSON response
        const responseText = await response.text();
        console.log("Raw response text:", responseText);

        let responseData;
        try {
            responseData = JSON.parse(responseText);
            console.log("Parsed response data:", responseData);
        } catch (e) {
            console.error("Error parsing JSON:", e);
            throw new Error("Invalid response format from server");
        }

        // Validate response data
        if (!responseData.token || !responseData.role) {
            console.error("Missing token or role in response:", responseData);
            throw new Error("Invalid response from server");
        }

        // Store the token and role in localStorage
        localStorage.setItem("token", responseData.token);
        localStorage.setItem("userRole", responseData.role);

        console.log("Stored credentials. Redirecting based on role:", responseData.role);

        // Redirect based on role
        const userRole = responseData.role.toUpperCase();
        switch (userRole) {
            case "CUSTOMER":
            case "USER":
                window.location.href = "customer/dashboard.html";
                break;
            case "SALON_ADMIN":
            case "OWNER":
                window.location.href = "owner/dashboard.html";
                break;
            case "GENERAL_ADMIN":
            case "ADMIN":
                window.location.href = "admin/dashboard.html";
                break;
            default:
                console.error("Unknown role:", userRole);
                throw new Error("Invalid user role detected");
        }
    } catch (error) {
        console.error("Error during login:", error);
        errorMessage.textContent = error.message || "Login failed. Please try again.";
        errorMessage.classList.remove("d-none");
    }
});
