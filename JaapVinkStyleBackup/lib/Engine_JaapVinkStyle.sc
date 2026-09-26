Engine_JaapVinkStyle : CroneEngine {
    var in, snd;
    var ff = 700.0, rmf = 3000.0, delayTime = 1.23, threshold = 0.1, amp = 1.0;
    var params, synth;

    alloc {
        SynthDef(\JaapVinkStyle, { |ff = 700, rmf = 100, delayTime = 1.23, threshold = 0.1, amp = 1.0|
            var in, snd;

            in = LocalIn.ar(2);

            snd = DelayC.ar(
                DFM1.ar(in, ff, res: 0.91, noiselevel: 0.001),
                5.0,
                delayTime
            );

            snd = snd * SinOsc.ar(rmf);

            snd = Compander.ar(
                snd, snd,
                thresh: threshold,
                slopeBelow: 1,
                slopeAbove: 0.1,
                clampTime: 1.0,
                relaxTime: 1.0
            );

            LocalOut.ar(snd);
            Out.ar(0, (snd * amp));
        }).add;

        Server.default.sync;

        params = Dictionary.newFrom([
            \ff, ff,
            \rmf, rmf,
            \delayTime, delayTime,
            \threshold, threshold,
            \amp, amp
        ]);

        this.addCommand(\ff, "f", { |msg|
            var v = msg[1];
            params[\ff] = v;
            if (synth.notNil) {
                synth.set(\ff, v);
            }
        });

        this.addCommand(\rmf, "f", { |msg|
            var v = msg[1];
            params[\rmf] = v;
            if (synth.notNil) {
                synth.set(\rmf, v);
            }
        });
        
        this.addCommand(\delayTime, "f", { |msg|
            var v = msg[1];
            params[\delayTime] = v;
            if (synth.notNil) {
                synth.set(\delayTime, v);
            }
        });
        
            this.addCommand(\threshold, "f", { |msg|
            var v = msg[1];
            params[\threshold] = v;
            if (synth.notNil) {
                synth.set(\threshold, v);
            }
        });
        
            this.addCommand(\amp, "f", { |msg|
            var v = msg[1];
            params[\amp] = v;
            if (synth.notNil) {
                synth.set(\amp, v);
            }
        });

        this.addCommand("start", "", {
            synth = Synth(\JaapVinkStyle, params.getPairs);
        });

        this.addCommand("stop", "", {
            if (synth.notNil) {
                synth.free;
                synth = nil;
            }
        });
    }


free

{

synth.free

}

}
