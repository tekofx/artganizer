import { Paper, Stack } from "@mui/material";
import React, { useContext, useRef } from "react";
import { DataContext } from "../../../pages/_app";
import { Submission } from "../../../interfaces";
import { useRouter } from "next/router";
interface BottomPanelProps {
  current: Submission;
}
export default function BottomPanel({ current }: BottomPanelProps) {
  const { data } = useContext(DataContext);
  const router = useRouter();
  const ref = useRef<HTMLDivElement>(null);

  const handleWheel = (e: React.WheelEvent) => {
    if (ref.current) {
      ref.current.scrollLeft += e.deltaY;
    }
  };
  return (
    <Paper
      sx={{ overflowX: "auto", maxHeight: "20vh" }}
      onWheel={handleWheel}
      ref={ref}
    >
      <Stack direction="row" sx={{ width: "max-content" }}>
        {data.submissions.map((submission) => (
          <img
            src={submission.image}
            key={submission.id}
            height="150px"
            style={{
              border: submission.id == current.id ? "5px solid #555" : "none",
              cursor: "pointer",
            }}
            onClick={() => router.push(`/submission/${submission.id}`)}
          />
        ))}
      </Stack>
    </Paper>
  );
}
