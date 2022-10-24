export namespace BMapGL {
    export class Map {
        constructor(elem: HTMLElement);
        addOverlay: (overlays: overlays) => void;
        setSenter: (Point: Point) => void;
        centerAndZoom: (Point: Point, scale: number) => void;
        enableScrollWheelZoom: (arg0: boolean) => void;
        addControl: (arg0: controls) => void;
        clearOverlays: () => void;
    }
    export class Point {
        constructor(lng: number, lat: number);
    }


    type overlays = Polygon | Marker;
    interface polygonInit {
        strokeColor?: string,
        strokeWeight: number,
        strokeOpacity: number,
    }
    export class Polygon {
        constructor(arg0: Point, init: polygonInit)
    }
    export class Marker {
        constructor(arg0: Point)
    }


    type controls = ZoomControl;
    export class ZoomControl {
        constructor()
    }


    export class Convertor {
        constructor();
        translate: (arg0: Point[],
            from: 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8,
            to: 3 | 5 | 6,
            fn: (res: { status: number, result: Point[] }) => void) => void
    }
}