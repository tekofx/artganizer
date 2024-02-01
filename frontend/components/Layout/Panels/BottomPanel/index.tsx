import { Paper, Stack } from "@mui/material";
import { useRouter } from "next/router";
import React, { useRef } from "react";
import { Submission } from "../../../../interfaces";
import { useAppContext } from "../../../../pages/_app";
interface BottomPanelProps {
  current: Submission;
}
export default function BottomPanel({ current }: BottomPanelProps) {
  const router = useRouter();
  const ref = useRef<HTMLDivElement>(null);
  const { submissions } = useAppContext();

  const handleWheel = (e: React.WheelEvent) => {
    if (ref.current) {
      ref.current.scrollLeft += e.deltaY;
    }
  };
  return (
    <Paper
      sx={{ overflowX: "auto", maxHeight: "20vh", height: "100%" }}
      onWheel={handleWheel}
      ref={ref}
    >
      <Stack direction="row" sx={{ width: "max-content" }}>
        {submissions.map((submission) => (
          <img
            key={submission.id}
            src={submission.thumbnail}
            height="150px"
            style={{
              border: submission.id == current.id ? "10px solid #555" : "none",
              cursor: "pointer",
            }}
            onClick={() => router.push(`/submissions/${submission.id}`)}
          />
        ))}
      </Stack>
    </Paper>
  );
}
