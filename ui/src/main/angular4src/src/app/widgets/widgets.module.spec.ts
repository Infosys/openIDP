import { WidgetsModule } from './widgets.module';

describe('WidgetsModule', () => {
  let widgetsModule: WidgetsModule;

  beforeEach(() => {
    widgetsModule = new WidgetsModule();
  });

  it('should create an instance', () => {
    expect(widgetsModule).toBeTruthy();
  });
});
