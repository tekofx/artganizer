import Submission from "../interfaces/Submission";

function hexToRgb(hex: string) {
  const r = parseInt(hex.slice(1, 3), 16);
  const g = parseInt(hex.slice(3, 5), 16);
  const b = parseInt(hex.slice(5, 7), 16);
  return [r, g, b];
}

function colorDistance(color1: string, color2: string) {
  const [r1, g1, b1] = hexToRgb(color1);
  const [r2, g2, b2] = hexToRgb(color2);
  return Math.sqrt((r2 - r1) ** 2 + (g2 - g1) ** 2 + (b2 - b1) ** 2);
}

function filterSubmissionsByColor(
  submissions: Submission[],
  color: string,
  threshold: number
): Submission[] {
  return submissions.filter((submission) =>
    submission.colors.some((c: string) => colorDistance(c, color) <= threshold)
  );
}

export { filterSubmissionsByColor };
