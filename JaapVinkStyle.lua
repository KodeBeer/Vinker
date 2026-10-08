-- ~An homage to Jaap Vink~
--
--
--     ------------[AMP]-[OUT]
--     ↓         ↑
--    [DEL]  [COMPR]
--       ↓   ↑   ↑
-- [~]--[RM]-------
engine.name = "JaapVinkStyle"

function init()
  print("JaapVinkStyle engine loaded")

  redraw()

  params:add{
    type = "control",
    id = "ff",
    name = "ff",
    controlspec = controlspec.new(50, 15000, "exp", 0, 700),
    action = function(v) engine.ff(v) end
  };

  params:add{
    type = "control",
    id = "rmf",
    name = "rmf",
    controlspec = controlspec.new(60, 6000, "exp", 0, 100),
    action = function(v) engine.rmf(v) end
  };

  params:add{
    type = "control",
    id = "delayTime",
    name = "delay Time",
    controlspec = controlspec.new(0.1, 1.35, "lin", 0.01, 0.5),
    action = function(v) engine.delayTime(v) end
  };

  params:add{
    type = "control",
    id = "threshold",
    name = "threshold",
    controlspec = controlspec.new(0, 1, "lin", 0.01, 1),
    action = function(v) engine.threshold(v) end
  };

  params:add{
    type = "control",
    id = "amp",
    name = "amp",
    controlspec = controlspec.new(0, 1, "lin", 0.01, 1),
    action = function(v) engine.amp(v) end
  };


  -- Auto-start the synth
  engine.start()
end

function enc(n, d)
  if n == 1 then
    params:delta("ff", d)
  elseif n == 2 then
    params:delta("rmf", d)
  elseif n == 3 then
    params:delta("delayTime", d)
  end
  redraw()
end

function redraw()
  screen.clear()

  screen.move(10, 20)
  screen.text(string.format("Filter Frequency: %.2f", params:get("ff")))

  screen.move(10, 40)
  screen.text(string.format("Ringmod Frequency: %.2f", params:get("rmf")))
  
  screen.move(10, 60)
  screen.text(string.format("Delay Time: %.2f", params:get("delayTime")))

  screen.update()
end

function cleanup()
  engine.stop()
end
