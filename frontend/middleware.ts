import type { NextRequest } from "next/server";
import { NextResponse } from "next/server";

export async function middleware(request: NextRequest) {
  const token = request.cookies.get("TOKEN");

  if (!token) {
    return NextResponse.redirect(new URL("/login", request.url));
  }

  fetch("http://localhost:3000/api/login/verify", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ token: token.value }),
  })
    .then((response) => {
      if (response.status !== 200) {
        return NextResponse.redirect(new URL("/login", request.url));
      }
      return NextResponse.next();
    })
    .catch((error) => {
      console.error(error);
      return NextResponse.redirect(new URL("/login", request.url));
    });
}

export const config = {
  matcher: [
    "/",
    "/settings",
    "/submissions/:path*", // This is a catch all for /submissions and /submissions/anything
    "/tags",
    "/characters",
    "/artists",
  ],
};
