import type { NextRequest } from "next/server";
import { NextResponse } from "next/server";

export function middleware(request: NextRequest) {
  const cookies = request.cookies;
  const currentUser = cookies.get("TOKEN");

  if (!currentUser) {
    return NextResponse.redirect(new URL("/login", request.url));
  }
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
