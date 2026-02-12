const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

function getToken() {
  return localStorage.getItem("token");
}

function crearHeaders(extraHeaders = {}) {
  const token = getToken();
  return {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
    ...extraHeaders,
  };
}

export async function apiRequest(path, options = {}) {
  const { metodo = "GET", body, headers = {} } = options;

  const url = `${BASE_URL}${path}`;

  try {
    const res = await fetch(url, {
      method: metodo, 
      headers: crearHeaders(headers),
      body: body !== undefined ? JSON.stringify(body) : undefined,
    });

    if (res.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("rol");
      window.location.href = "/";
    }

    if (!res.ok) {
      const contentType = res.headers.get("content-type") || "";
      let message = `Error ${res.status}`;

      try {
        if (contentType.includes("application/json")) {
          const errorPayload = await res.json();
          message =
            errorPayload.message ||
            errorPayload.error ||
            errorPayload.mensaje ||
            message;
        } else {
          const errorText = await res.text();
          message = errorText || message;
        }
      } catch (e) {
        
      }

      throw new Error(message);
    }

    if (res.status === 204) return null;

    const contentType = res.headers.get("content-type") || "";
    if (contentType.includes("application/json")) {
      return await res.json(); 
    }

    return await res.text();
  } catch (error) {
    throw error;
  }
}
