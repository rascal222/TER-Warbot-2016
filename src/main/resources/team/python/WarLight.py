class WarLight(PyWarLight):

    def __init__(self):
        pass

    def action(self):
        global WA
        WA = self;
        return actionWarLight();